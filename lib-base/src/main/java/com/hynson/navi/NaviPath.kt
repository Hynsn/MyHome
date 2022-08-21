package com.hynson.navi

class NaviPath {
    companion object {
        private const val GROUP_APP = "/app/"

        const val APP_LAUNCH = "${GROUP_APP}launch"

        private const val GROUP_DEVICE = "/device/"
        const val DEVICE_HOME = "${GROUP_DEVICE}home"
        const val DEVICE_FRAG1 = "${GROUP_DEVICE}frag1"

        private const val GROUP_SETTING = "/setting/"
        const val SETTING_FRAG1 = "${GROUP_SETTING}frag1"
        const val SETTING_FRAG2 = "${GROUP_SETTING}frag2"
    }
}
