// # Define a tag que inicia o pipeline
// pipeline {
node {
    // # Define o agent que vai ser executado, no caso abaixo qualquer um
    agent any


    // # Os stages que vão ter na nossa pipeline no momento em que estiver em execução.
    // stages {
        // # Agora dentro da tag stages que colocamos cada passo "stage" a ser feito pelo nosso pipeline.
        stage ('Build Backend') {
            withMaven(maven: 'MAVEN_INSTALADO_CONTAINER') {
                // # Os steps são os passos que nosso stage vai fazer.
                
                    // # No comando abaixo estamos falando par o maven realizar uma limpeza antes de realizar o build. O parâmetro skipTest faz que não rode o test nesse momento.
                    sh 'mvn clean package -DskipTest=true'
               
            }  
        }
    // }
}