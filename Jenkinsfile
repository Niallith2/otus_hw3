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
                    currentBuild.description = "USER: ${env.BUILD_USER}\nBRANCH: ${env.BRANCH}"
                    sh """
                        /kaniko/executor \
                        --context=dir://. \
                        --dockerfile=Dockerfile \
                        --cleanup \
                        --insecure \
                        --build-arg=URL=${params.URL}
                        --destination=registry.kube-system.svc.cluster.local/otus_hw3:${env.BRANCH}
                    """
                }
            }
        }
    }
}