pipeline {
    agent any

    environment {
        SHELL_DIR = "shell-script"
        SERVICE_DIR = "service"
        TASK_DIR = "task"
        TASK_DEFINITION_DIR = "task-definition"
    }

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['prod', 'dev'], description: '')
        choice(name: 'SERVICE', choices: ['zipsa'], description: '')
        string(name: 'TAG_NAME', defaultValue: '', description: '')

        booleanParam(name: 'REGISTER_TASK_DEFINITION', defaultValue: true, description: '')
        booleanParam(name: 'CREATE_NEW_SERVICE', defaultValue: false, description: '')
        booleanParam(name: 'UPDATE_SERVICE', defaultValue: true, description: '')
    }

    stages {

        stage('Init') {
            steps {
                script {
                    sh """
                    sed -i 's/tag-name/${params.TAG_NAME}/g' json/register-task-definition/zipsa-prod.json
                    """
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
                    sh """
                    aws ecs register-task-definition --cli-input-json file://${env.WORKSPACE}/json/register-task-definition/${params.SERVICE}-${params.ENVIRONMENT}.json
                    """
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
                    sh """
                    aws ecs create-service --cli-input-json file://${env.WORKSPACE}/json/create-service/${params.SERVICE}-${params.ENVIRONMENT}.json
                    """
                }
            }
        }

        stage('Update Service') {
            when {
                expression {
                    params.UPDATE_SERVICE
                }
            }
            steps {
                script {
                    sh """
                    aws ecs update-service --service ${params.SERVICE}-${params.ENVIRONMENT} --cli-input-json file://${env.WORKSPACE}/json/update-service/${params.SERVICE}-${params.ENVIRONMENT}.json
                    """
                }
            }
        }

    }
}
