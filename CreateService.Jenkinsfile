def ms

pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['prod', 'dev'], description: '')
        choice(name: 'SERVICE', choices: ['zipsa'], description: '')
        string(name: 'TAG_NAME', defaultValue: '', description: '')

        booleanParam(name: 'REGISTER_TASK_DEFINITION', defaultValue: true, description: '')
        booleanParam(name: 'CREATE_NEW_SERVICE', defaultValue: false, description: '')
    }

    stages {

        stage('Init') {
            steps {
                script {
                    ms = load "MainScript.groovy"
                    ms.init(params.TAG_NAME)

                    // sh """
                    // sed -i 's/tag-name/${params.TAG_NAME}/g' json/register-task-definition/zipsa-prod.json
                    // """
                }
            }
        }

        stage('Register Task Definition') {
            when {
                expression {
                    params.REGISTER_TASK_DEFINITION
                }
            }
            steps {
                script {
                    ms.registerTaskDefinition(params.SERVICE, params.ENVIRONMENT)

                    // sh """
                    // aws ecs register-task-definition --cli-input-json file://${env.WORKSPACE}/json/register-task-definition/${params.SERVICE}-${params.ENVIRONMENT}.json
                    // """
                }
            }
        }

        stage('Create Service') {
            when {
                expression {
                    params.CREATE_NEW_SERVICE
                }
            }
            steps {
                script {
                    ms.createService(params.SERVICE, params.ENVIRONMENT)

                    // sh """
                    // aws ecs create-service --cli-input-json file://${env.WORKSPACE}/json/create-service/${params.SERVICE}-${params.ENVIRONMENT}.json
                    // """
                }
            }
        }

    }
}
