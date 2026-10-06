pipeline {
    agent {
        kubernetes {
            cloud "otus"
            inheritFrom "kaniko"
            defaultContainer "kaniko"
        }
    }
    parameters {
        string(name: 'URL',
               defaultValue: 'https://fakerestapi.azurewebsites.net',
               description: 'Base URL для API-тестов')
    }
    stages {
        stage("Build Docker image") {
            steps {
                script {
                    def branch = env.BRANCH_NAME ?: env.GIT_BRANCH ?: 'master'
                    def currentCommit = env.GIT_COMMIT
                    def previousCommit = env.GIT_PREVIOUS_COMMIT ?: ''
                    def imageTag = "registry.kube-system.svc.cluster.local/otus_hw3:${branch}"

                    currentBuild.description = "BRANCH: ${branch}\nCOMMIT: ${currentCommit}\nURL: ${params.URL}"

                    // Пропускаем, если коммит не менялся
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

                    sh """
                        /kaniko/executor \
                        --context=dir://. \
                        --dockerfile=Dockerfile \
                        --cleanup \
                        --insecure \
                        --build-arg=URL=${params.URL} \
                        --destination=${imageTag}
                    """
                }
            }
        }
    }
}