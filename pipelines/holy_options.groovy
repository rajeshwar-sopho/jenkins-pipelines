pipeline {
    agent any   // run on any available agent

    environment {
        GREETING = "Hello Jenkins"
        BUILD_NUMBER_STR = "${env.BUILD_NUMBER}"
    }

    options {
        timeout(time: 10, unit: 'MINUTES')   // abort pipeline if it takes too long
        buildDiscarder(logRotator(numToKeepStr: '5')) // keep last 5 builds
        disableConcurrentBuilds()           // avoid parallel runs
        skipDefaultCheckout()               // don’t auto-checkout SCM
        ansiColor('xterm')                  // enable ANSI colors
    }

    stages {
        stage('Init') {
            steps {
                echo "Running on agent: ${env.NODE_NAME}"
                echo "Message: ${env.GREETING}, build #${env.BUILD_NUMBER_STR}"
                echo "Selected ENV: ${params.ENV}"
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Deploy') {
            when { branch 'main' } // only deploy on main branch
            steps {
                echo "Deploying to ${params.ENV} environment"
            }
        }
    }
}
