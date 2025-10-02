pipeline {
    agent none

    stages {
        stage('Any Agent Stage') {
            agent any
            steps {
                echo "Running on agent: ${env.NODE_NAME}"
            }
        }
        stage('Test Agent Stage') {
            agent {
                label 'test-agent'
            }
            steps {
                echo "Running on agent: ${env.NODE_NAME}"
            }
        }
        // this stage does not work as using Built-in agent does not seem to be supported in Jenkins 
        // stage('Built-in Agent Stage') {
        //     agent {
        //         label 'built-in'
        //     }
        //     options {
        //         timeout(time: 15, unit: 'SECONDS')
        //     }
        //     steps {
        //         echo "Running on agent: ${env.NODE_NAME}"
        //     }
        // }
        stage('Docker Agent Stage') {
            agent {
                docker {
                    image 'alpine:latest'
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                    label 'test-agent' // Specify a label if needed
                }
            }
            steps {
                sh 'echo "Running inside Docker container"'
                sh 'echo "Container ID: $(cat /proc/self/cgroup | grep "docker" | sed s/\\//\\n/g | tail -1)"'
            }
        }
        stage('dockerfile Agent Stage') {
            agent {
                dockerfile {
                    filename 'Dockerfile'
                    dir 'build'
                    // label 'my-defined-label-2' if label is not specified, it will use the default agent
                    additionalBuildArgs '--build-arg version=1.0.2'
                }
            }
            steps {
                sh 'echo "Running inside Docker container built from Dockerfile"'
                sh 'echo "Container ID: $(cat /proc/self/cgroup | grep "docker" | sed s/\\//\\n/g | tail -1)"'
            }
        }
    }
}