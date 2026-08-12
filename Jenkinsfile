pipeline {
    agent any
    tools {
        gradle '9.7.0'
    }
    stages {
        stage('Hello') {
            steps {
                sh 'gradle --version'
            }
        }
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build') {
            steps {
                sh '''
                    gradle build
                '''
            }
        }
    }
}
