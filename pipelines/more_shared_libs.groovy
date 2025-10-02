@Library('my-shared-lib') _
pipeline {
    agent any

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'qa', 'prod'],
            description: 'Select the environment to load'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the repository so the config file is available
                checkout scm
            }
        }

        stage('Load Env') {
            steps {
                sh "ls"
                sh "pwd"
                script {
                    // Use selected environment from parameters
                    loadYamlEnv('./config/config.yaml', params.ENVIRONMENT)
                }
            }
        }

        stage('Use Env') {
            steps {
                echo "DB_HOST=${env.DB_HOST}"
                echo "API_KEY=${env.API_KEY}"
            }
        }
    }
}
