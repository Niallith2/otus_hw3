pipeline {
    agent { kubernetes { cloud "otus"; inheritFrom "kaniko"; defaultContainer "kaniko" } }
    parameters {
        string(name: 'URL', defaultValue: 'https://fakerestapi.azurewebsites.net', description: 'Base URL')
    }
    stages {
        stage("Build Docker image") {
            steps {
                script {
                    def branch = env.BRANCH ?: params.BRANCH ?: 'master'
                    def imageTag = "registry.kube-system.svc.cluster.local/otus_hw3:${branch}"
                    def currentCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()

                    // 1. Проверка наличия образа в реестре
                    def imageExists = sh(
                        returnStatus: true,
                        script: "crane manifest ${imageTag} > /dev/null 2>&1"
                    ) == 0

                    // 2. Проверка наличия новых коммитов
                    def lastCommit = ''
                    if (fileExists('last_built_commit.txt')) {
                        lastCommit = readFile('last_built_commit.txt').trim()
                    }
                    def hasNewCommits = (currentCommit != lastCommit)

                    if (imageExists && !hasNewCommits) {
                        echo "Образ уже есть в реестре, новых коммитов нет — пропускаем."
                        return
                    }

                    // 3. Сборка
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