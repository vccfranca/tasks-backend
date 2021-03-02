// # Define a tag que inicia o pipeline
pipeline {
    // # Define o agent que vai ser executado, no caso abaixo qualquer um
    agent any

     tools {
        maven "MAVEN_INSTALADO_CONTAINER"
    }
    
    // # Os stages que vão ter na nossa pipeline no momento em que estiver em execução.
    stages {
        // # Agora dentro da tag stages que colocamos cada passo "stage" a ser feito pelo nosso pipeline.
        stage ('Build Backend') {
            // # Os steps são os passos que nosso stage vai fazer.
            steps {
                // # No comando abaixo estamos falando par o maven realizar uma limpeza antes de realizar o build. O parâmetro skipTest faz que não rode o test nesse momento.
                sh 'mvn clean package -DskipTest=true'
            }
        }
        stage ('Unit Testes') {
            steps {
                // # Ao executar o teste aqui temos de prestar atenção para não colocar o clena antes, pois o mesmo vai apagar o conteudo da pasta target gerado no build
                sh 'mvn test'
            }
        }
        stage ('Check Dependencias com OWASP') {
            steps {
                dependencyCheck additionalArguments: '', odcInstallation: 'Owasp-6.1.1'
            }
        }
        Stage ('Publicando Resultados OWAS') {
            steps {
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }    
        }

    }
}