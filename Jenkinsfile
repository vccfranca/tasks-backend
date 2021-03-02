// # Define a tag que inicia o pipeline
pipeline {
    nome_projeto="DeployBack"
    sonar_login="09eb73b479ebf07efc6f91a8c1522943773ece4f"
    sonar_host="http://192.168.0.121:9000"

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
        stage ('Publicando Resultados OWAS') {
            steps {
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }    
        }
        stage ('Sonar Analise') {
            // # Criando uma varivael para rodar o sonar configurado de acordo com o Global Toll Configuration do Jenkins
            environment {
                scannerSonar = tool 'sonar-scanner'
            }
            steps {
                withSonarQubeEnv('sonar-qualitygate') {
                    sh "${scannerSonar}/bin/sonar-scanner -e mvn -X sonar:sonar \
                        -e sonar.projectKey="${nome_projeto}" \
                        -e sonar.host.url="${sonar_host}" \
                        -e sonar.login="${sonar_login}" \
                        -e sonar.java.binaries="target" \
                        -e sonar.covarege.exclusions=**/mvn/**,**/scr/teste/**,**/model/**,**/Application.java"
                }
            }
        }

    }
}






