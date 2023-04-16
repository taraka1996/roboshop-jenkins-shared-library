
def call () {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts=""
    }
    node('workstation') {

        stage('compile/build') {
            common.compile()

        
      }

        stage('Test Cases') {
           common.testcases()
      }
                }
            