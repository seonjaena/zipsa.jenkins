def ms

pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['prod', 'dev'], description: '')
        choice(name: 'SERVICE', choices: ['zipsa'], description: '')

        booleanParam(name: 'ROLLING_UPDATE_SERVICE', defaultValue: true, description: '')
        booleanParam(name: 'CANARY_UPDATE_SERVICE', defaultValue: false, description: '')
    }

    stages {

        stage('Init') {
            steps {
                script {
                    ms = load "MainScript.groovy"
                    ms.init("")
                }
            }
        }

        stage('Rolling Update Service') {
            when {
                expression {
                    params.ROLLING_UPDATE_SERVICE
                }
            }
            steps {
                script {
                    ms.rollingUpdate(params.SERVICE, params.ENVIRONMENT)
                }
            }
        }
    }
}
