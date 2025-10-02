@Library('my-shared-lib') _
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                buildApp('myApp')   // call shared library function
            }
        }
    }
}
