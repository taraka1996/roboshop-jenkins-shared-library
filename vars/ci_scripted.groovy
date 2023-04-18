def call() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts=""
    }
    if(env.TAG_NAME !=~ ".*") {
        env.GTAG = "true"
    } else {
        env.GTAG = 'false'
    }

    node('workstation') {
        try {
            stage('check out code') {                      
                cleanWs()                                
                git branch: 'main', url: 'http://github.com/taraka1996/cart'
                             
            } 

            sh 'env' 

            if (env.BRANCH_NAME != "main" ) {
                stage('compile/build') {            
                    common.compile()
                }        
            } 

            println GTAG
            println BRANCH_NAME      

            if(env.GTAG != "true" && env.BRANCH_NAME != "main" ) {
                stage('Test Cases') {
                    common.testcases()
                }
            }

            if (BRANCH_NAME ==~ "PR-.*") {              
                stage('code quality') {
                    common.codequality()
                }
                
                if(env.GTAG != "true"  ) {
                    stage('package') {
                        common.prepareArtifacts()
                    }
                    stage('Artifact upload') {
                        common.testcases()
                    }
                }
            }
        } catch (e) {
            mail body: "<h1>${component} - Pipeline Failed \n ${BUILD_URL}</h1>", from: 'tarakaramtirumala@gmail.com', subject: "${component}- Pipeline Failed", to: 'tarakaramtirumala@gmail.com',  mimeType: 'text/html'
        }
    }
}
