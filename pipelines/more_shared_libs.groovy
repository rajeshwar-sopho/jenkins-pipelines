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
        stage('Load Env') {
            steps {
                script {
                    // Use selected environment from parameters
                    loadYamlEnv('config/config.yaml', params.ENVIRONMENT)
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
