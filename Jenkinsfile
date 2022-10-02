pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Build JAR File') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/longboardAL/Mingeso']]])
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Build Docker Image') {
            steps {
                bat 'docker build -t angelavendano/entrega_1 .'
            }
        }
        stage('Push docker image') {
            steps {
                withCredentials([string(credentialsId: 'dckr-hubpassword', variable: 'dckpass')]) {
                    bat 'docker login -u angelavendano -p %dckpass%'
                }
                bat 'docker push angelavendano/entrega_1'
            }
        }
    }
    post {
        always {
            bat 'docker logout'
        }
    }
}