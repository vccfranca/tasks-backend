// # Define a tag que inicia o pipeline
pipeline {
    // # Define o agent que vai ser executado, no caso abaixo qualquer um
    agent any
    // # Os stages que vão ter na nossa pipeline no momento em que estiver em execução.
    stages {
        // # Agora dentro da tag stages que colocamos cada passo "stage" a ser feito pelo nosso pipeline.
        stage ('Build Backend') {
            // # Os steps são os passos que nosso stage vai fazer.
            steps {
                sh 'mvn clean package'
            }
        }
    }
}