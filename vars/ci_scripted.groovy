def call() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts=""
    }
    node('workstation') {
        try {
            stage('check out code') {                
                cleanWs()                
                git branch: 'main', url: 'http://github.com/taraka1996/cart'
                
            }  
            sh 'env'
            if (env.BRANCH_NAME != "main") {
                stage('compile/build') {            
                    common.compile()
                }        
            }
            stage('Test Cases') {
                common.testcases()
            }
            stage('code quality') {
                common.codequality()
            }
        } catch (e) {
            mail body: "<h1>${component} - Pipeline Failed \n ${BUILD_URL}</h1>", from: 'tarakaramtirumala@gmail.com', subject: "${component}- Pipeline Failed", to: 'tarakaramtirumala@gmail.com',  mimeType: 'text/html'
        }
    }
}
