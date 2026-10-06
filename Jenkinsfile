pipeline {
    agent {
        kubernetes {
            cloud "otus"
            inheritFrom "kaniko"
            defaultContainer "kaniko"
        }
    }
    stages {
        stage("Build Docker image") {
            steps {
                script {
                    currentBuild.description = "USER: ${env.BUILD_USER}\nBRANCH: ${env.BRANCH}"
                    sh """
                        /kaniko/executor \
                        --context=dir://. \
                        --dockerfile=registry.kube-system.svc.cluster.local/otus_hw3:${env.BRANCH} \
                        --cleanup \
                        --insecure \
                        --destination=registry.kube-system.svc.cluster.local/otus_hw3:${env.BRANCH}
                    """
                }
            }
        }
    }
}