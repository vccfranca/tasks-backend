// # Define a tag que inicia o pipeline
pipeline {
    // # Definindo variaveis para serem usadas dentro dos steps
     environment {
                scannerHome = tool "sonar-scanner"
                nome_projeto="DeployBack"
                //sonar_login="09eb73b479ebf07efc6f91a8c1522943773ece4f"
                sonar_login="62cbbfd2f357c6897f9b54ee175f7e360bb6e937"
                sonar_host="http://192.168.0.121:9000"
                SONAR_EXCLUSIONS="**/mvn/**,**/scr/teste/**,**/model/**,**/Application.java"
    }

    // # Define o agent que vai ser executado, no caso abaixo qualquer um
    agent any

     tools {
        maven "MAVEN_INSTALADO_CONTAINER"
        jdk "JDK-11"
        
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
            steps {
                withSonarQubeEnv('sonar-community') {
                    sh "${scannerHome}/bin/sonar-scanner " +
                         "-Dsonar.projectKey=${nome_projeto} " +
                         "-Dsonar.host.url=${sonar_host} " +
                         "-Dsonar.login=${sonar_login} " +
                         "-Dsonar.java.binaries=target " +
                         "-Dsonar.covarege.exclusions=$SONAR_EXCLUSIONS"
                         
                }
            }
        }

    }
}






