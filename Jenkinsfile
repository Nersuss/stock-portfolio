pipeline {
    agent any
    tools {
        gradle '9.7.0'
    }
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build & test') {
            steps {
                sh '''
                    ./gradlew clean bootJar --no-daemon
                '''
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    docker compose build
                '''
            }
        }
    }
}
