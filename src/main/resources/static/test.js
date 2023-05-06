import {initializeApp} from "firebase/app";

import {getAuth, getRedirectResult, FacebookAuthProvider, signInWithRedirect} from "firebase/auth";
// Import the functions you need from the SDKs you need
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyCFqoRJBBv8vtK2tlrKE3VucOx0wj_GiAk",
    authDomain: "trade-app-d4d02.firebaseapp.com",
    projectId: "trade-app-d4d02",
    storageBucket: "trade-app-d4d02.appspot.com",
    messagingSenderId: "1028350768426",
    appId: "1:1028350768426:web:eee1ac3d810681892f1a53",
    measurementId: "G-WNGJND5WZV"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

const provider = new FacebookAuthProvider();

provider.addScope('user_birthday');

const auth = getAuth();
signInWithRedirect(auth, provider);
getRedirectResult(auth)
    .then((result) => {
        // This gives you a Facebook Access Token. You can use it to access the Facebook API.
        const credential = FacebookAuthProvider.credentialFromResult(result);
        const token = credential.accessToken;

        const user = result.user;
        // IdP data available using getAdditionalUserInfo(result)
        // ...
    }).catch((error) => {
    // Handle Errors here.
    const errorCode = error.code;
    const errorMessage = error.message;
    // The email of the user's account used.
    const email = error.customData.email;
    // AuthCredential type that was used.
    const credential = FacebookAuthProvider.credentialFromError(error);
    // ...
});