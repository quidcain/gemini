const BUILD_VERSION = require('../package.json').version;
const BASE_CONTEXT = process.env.REACT_APP_BASE_CONTEXT;
const REST_URL = process.env.REACT_APP_API;
const SITE_KEY = process.env.REACT_APP_SITE_KEY;

console.log(`SIE-Registro App Version: ${BUILD_VERSION}\nEnv Args:\n\tBASE_CONTEXT=(${BASE_CONTEXT}) \n\tREST_URL=(${REST_URL}) \n\tSITE_KEY=(${SITE_KEY})`);

let config = {
    buildVersion: BUILD_VERSION,
    baseContext: BASE_CONTEXT,
    restServer: REST_URL,
    reCAPTCHASiteKey: SITE_KEY,
    loadingMinimumTime: 1000,
    userActionPath: ["/login", "/registration", "/activate", "/forgot", "/reset", "/cancel"],
};

config.publicPaths = ["/", ...config.userActionPath];


export function isPublicUrl(currentPath) {
    let isPublic = false;
    for (let path of config.publicPaths) {
        if (path !== "/" && currentPath.startsWith(path))
            return true;
        else
            isPublic |= path === currentPath;
    }
    return isPublic;
}

export function isUserActionUrl(currentPath) {
    let isPublic = false;
    for (let path of config.publicPaths) {
        if (path !== "/" && currentPath.startsWith(path))
            return true;
        else
            isPublic |= path === currentPath;
    }
    return isPublic;
}

export default config;
