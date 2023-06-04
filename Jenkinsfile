pipeline {
    agent any
    tools{
        maven 'mvn'
    }
    stages {
        stage('build') {
            steps {
                script {
                    echo "building the application..."
                    bat "mvn package"
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    echo "Deploying the application..."
                    bat "mvn spring-boot:run"
                }
            }
        }
    }
}