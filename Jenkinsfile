pipeline {
    agent any
    tools {
        gradle '9.7.0'
    }
    stages {
//         stage('Hello') {
//             steps {
//                 sh 'gradle --version'
//             }
//         }
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Gradle build') {
            steps {
                sh '''
                    gradle clean bootJar -x test --no-daemon
                '''
            }
        }
        stage('Docker build') {
            steps {
                sh '''
                    docker build -t stock
                '''
            }
        }
        stage('Docker-compose build') {
            steps {
                sh '''
                    docker compose build
                '''
            }
        }
//         stage('Tests') {
//             steps {
//                 echo 'Tests...'
//             }
//         }
    }
}
