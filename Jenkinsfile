pipeline {
    agent any
    tools {
        maven 'Maven3'   // must match the Maven name in Jenkins Global Tool Config
        jdk 'JDK21'      // must match the JDK name in Jenkins Global Tool Config
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
