pipeline {
    agent {
        kubernetes {
            cloud "otus"
            inheritFrom "default"
            yaml """
                    apiVersion: v1
                    kind: Pod
                    spec:
                        containers:
                            - name: tests
                              image: localhost:5000/otus_hw3:${branch}
                              imagePullPolicy: Always
                              command: ["sleep"]
                              args: ["infinity"]
                    """
        }
    }
    parameters {
        string(name: 'BRANCH', defaultValue: 'master', description: 'Branch')
        string(name: 'URL', defaultValue: 'https://fakerestapi.azurewebsites.net', description: 'API Url')
        booleanParam(name: 'FORCE', defaultValue: false, description: 'Принудительно пересобрать образ')
    }

    stages {
        stage("Build Docker image") {
            agent {
                kubernetes {
                    cloud "otus"
                    inheritFrom "kaniko"
                    defaultContainer "kaniko"
                }
            }
            steps {
                script {
                    def branch          = params.BRANCH ?: 'master'
                    def currentCommit   = env.GIT_COMMIT
                    def previousCommit  = env.GIT_PREVIOUS_COMMIT ?: ''
                    def force           = params.FORCE
                    def imageTag        = "registry.kube-system.svc.cluster.local/otus_hw3:${branch}"

                    currentBuild.description =
                        "BRANCH: ${branch}\nCOMMIT: ${currentCommit}\nURL: ${params.URL}\nFORCE: ${force}"

                    if (force) {
                        echo "FORCE=true — принудительная сборка, пропускаем проверки."
                    } else {
                        if (previousCommit && previousCommit == currentCommit) {
                            echo "Коммит ${currentCommit} уже собирался — пропускаем."
                            return
                        }

                        def imageExists = sh(
                            returnStatus: true,
                            script: """
                                curl -sf -o /dev/null \
                                -H 'Accept: application/vnd.docker.distribution.manifest.v2+json' \
                                http://registry.kube-system.svc.cluster.local/v2/otus_hw3/manifests/${branch}
                            """
                        ) == 0

                        if (imageExists && !previousCommit) {
                            echo "Образ ${imageTag} уже есть в реестре — пропускаем."
                            return
                        }
                    }

                    sh """
                        /kaniko/executor \
                        --context=dir://. \
                        --dockerfile=Dockerfile \
                        --cleanup \
                        --insecure \
                        --skip-tls-verify-pull \
                        --skip-tls-verify \
                        --destination=${imageTag}
                    """
                }
            }
        }
        stage('Run tests') {
            steps {
                container("tests") {
                    catchError(buildResult: "UNSTABLE", stageResult: "SUCCESS"){
                        script {
                            withEnv([
                                "PROFILE=RestAssuredTests",
                                "URL=${params.URL}"
                            ]) {
                                sh "/otus_hw3/entrypoint.sh"
                            }
                            sh "chmod -R 777 target || true"
                        }
                    }
                }
            }
        }
        stage("Generate allure report") {
            steps {
                catchError(buildResult: "UNSTABLE", stageResult: "SUCCESS"){
                    script {
                        allure(
                            results: [[path: "target/allure-results"]],
                            disabled: false,
                            reportBuildPolicy: "ALWAYS"

                       )
                    }
                }
            }
        }
    }
}