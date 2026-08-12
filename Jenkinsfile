pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build') {
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
