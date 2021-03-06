// # Define a tag que inicia o pipeline
pipeline {
    // # Definindo variaveis para serem usadas dentro dos steps
     environment {
                scannerInicial = tool "sonar-scanner"
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
        // stage ('Unit Testes') {
        //     steps {
        //         // # Ao executar o teste aqui temos de prestar atenção para não colocar o clena antes, pois o mesmo vai apagar o conteudo da pasta target gerado no build
        //         sh 'mvn test'
        //     }
        // }
        // stage ('Check Dependencias com OWASP') {
        //     steps {
        //         dependencyCheck additionalArguments: '', odcInstallation: 'Owasp-6.1.1'
        //     }
        // }
        // stage ('Publicando Resultados OWAS') {
        //     steps {
        //         dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
        //     }    
        // }
        stage ('Sonar Analise') {
            steps {
                withSonarQubeEnv('sonar-community') {
                    sh "${scannerInicial}/bin/sonar-scanner " +
                         "-Dsonar.projectKey=${nome_projeto} " +
                         "-Dsonar.host.url=${sonar_host} " +
                         "-Dsonar.login=${sonar_login} " +
                         "-Dsonar.java.binaries=target " +
                         "-Dsonar.covarege.exclusions=$SONAR_EXCLUSIONS"
                         
                }
            }
        }
        stage ('Quality Gate')  {
            steps {
                sleep(40)
                timeout(time: 3, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                    

                }

            }
        }
        stage ('Deploy BackEnd'){
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat_9', path: '', url: 'http://192.168.0.121:8085/')], contextPath: 'task-backend', war: 'target/tasks-backend.war'
            }

        }
        stage ('API Test'){
            tools {
                jdk "JDK-8"
                maven "MAVEN_INSTALADO_CONTAINER"
            }
            steps {
                dir('api-test') {
                    git branch: 'main', credentialsId: '9322c792-9a8f-4227-a35f-c0a7abddf807', url: 'https://github.com/vccfranca/api-test'
                    sh 'mvn test'
                }
            }
        }
        stage ('Build FrontEnd') {
            steps {
                dir ('frontend') {
                    git branch: 'master', credentialsId: '9322c792-9a8f-4227-a35f-c0a7abddf807', url: 'https://github.com/vccfranca/tasks-frontend'
                    sh 'mvn clean package'
                }
                
            }
        }
        stage ('Deploy FrontEnd') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat_9', path: '', url: 'http://192.168.0.121:8085/')], contextPath: 'tasks', war: 'frontend/target/tasks.war'
            }
        }
        stage ('Testes Funcionais') {
            steps {
                dir('teste-funcional') {
                    git branch: 'main', credentialsId: '9322c792-9a8f-4227-a35f-c0a7abddf807', url: 'https://github.com/vccfranca/funcional-test'
                    sh 'mvn test'

                }
            }
        }
    }
}








