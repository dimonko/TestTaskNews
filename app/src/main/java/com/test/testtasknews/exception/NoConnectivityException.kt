package com.test.testtasknews.exception

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String
        get() = "No connectivity"

}