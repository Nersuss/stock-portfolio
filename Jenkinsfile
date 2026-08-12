pipeline {
    agent any
    tools {
        gradle '9.7.0'
    }
    stages {
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
        stage('Deploy (Docker-compose build)') {
            steps {
                sh '''
                    docker compose build
                '''
            }
        }
        stage('Tests') {
            steps {
                sh '''
                    docker compose -f docker-compose.yaml up -d db
                    sleep 10
                    docker compose -f docker-compose.yaml run --rm backend ./gradlew test
                '''
            }
        }
    }
}
