pipeline {
    agent any

    environment {
        GREETING = "Hello Jenkins"
    }

    parameters {
        string(name: 'USERNAME', defaultValue: 'guest', description: 'Enter username')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests?')
        choice(name: 'ENV', choices: ['dev', 'qa', 'prod'], description: 'Target environment')
    }

    options {
        timeout(time: 15, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
        skipDefaultCheckout()
    }

    stages {
        stage('Init') {
            steps {
                ansiColor('xterm') {
                    echo "${GREETING}, build #${env.BUILD_NUMBER} on node ${env.NODE_NAME}"
                    echo "Selected ENV: ${params.ENV}"
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            parallel {
                stage('Build') {
                    steps {
                        echo "Building project..."
                        sh 'echo Building...'
                    }
                }

                stage('Test') {
                    when { expression { return params.RUN_TESTS } }
                    steps {
                        echo "Running tests..."
                        sh 'echo Tests passed'
                    }
                }
            }
        }

        stage('Nested & Matrix Example') {
            stages {
                stage('Nested Stage 1') {
                    steps {
                        echo "Inside Nested Stage 1"
                    }
                }

                stage('Nested Stage 2 - Matrix') {
                    matrix {
                        axes {
                            axis {
                                name 'PYTHON_VERSION'
                                values '3.10', '3.11'
                            }
                            axis {
                                name 'OS'
                                values 'ubuntu', 'centos'
                            }
                        }
                        stages {
                            stage('Setup') {
                                steps {
                                    echo "Setting up environment: Python=${PYTHON_VERSION}, OS=${OS}"
                                }
                            }
                            stage('Run Tests') {
                                steps {
                                    echo "Running tests on Python=${PYTHON_VERSION}, OS=${OS}"
                                }
                            }
                        }
                    }
                }

                stage('Nested Stage 3 with when') {
                    when { branch 'main' }
                    steps {
                        echo "This nested stage runs only on main branch"
                    }
                }
            }
        }

        stage('Deploy') {
            when { expression { params.ENV == 'prod' } }
            steps {
                echo "Deploying to production environment"
            }
        }
    }

    post {
        always { echo "Always runs (cleanup, reports, etc.)" }
        success { echo "Pipeline succeeded!" }
        failure { echo "Pipeline failed!" }
        unstable { echo "Pipeline is unstable!" }
        aborted { echo "Pipeline was aborted!" }
        changed { echo "Pipeline result changed from previous run" }
        fixed { echo "Pipeline fixed from previous failed/unstable" }
        regression { echo "Pipeline regressed compared to previous run" }
    }
}
