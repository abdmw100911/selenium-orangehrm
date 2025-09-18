pipeline {
    agent any
    tools {
        maven 'Maven-3.9'   // exact name from Global Tool Config
        jdk 'JAVA_HOME'     // exact name from Global Tool Config
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh "mvn clean compile"
                    } else {
                        bat "mvn clean compile"
                    }
                }
            }
        }
        stage('Run Selenium Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh "mvn exec:java -Dexec.mainClass=com.example.selenium.LoginTest"
                    } else {
                        bat "mvn exec:java -Dexec.mainClass=com.example.selenium.LoginTest"
                    }
                }
            }
        }
    }
}
