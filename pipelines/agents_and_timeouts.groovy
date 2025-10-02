pipeline {
    agent any
    options {
        timeout(time: 10, unit: 'SECONDS')
    }
    stages {
        stage('Stage 1') {
            steps {
                echo 'Hello world!'
            }
        }
        stage('Stage 2') {
            steps {
                script {
                    // Simulate a long-running task
                    sleep 5
                }
            }
        }
        stage('Stage 3') {
            agent any
            options {
                timeout(time: 5, unit: 'SECONDS')
            }
            steps {
                // Simulate a long-running task
                sleep 5
            }
        }
    }    

}