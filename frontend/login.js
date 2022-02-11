import { initializeApp } from "firebase/app";
import { getAuth, onAuthStateChanged, signInWithEmailAndPassword, sendPasswordResetEmail, createUserWithEmailAndPassword} from 'firebase/auth';

var app;

window.ns = {
initApp: function(){
    const firebaseConfig = {
    apiKey: "AIzaSyC5gl_nk72vgOq2qNt7lbBHhHEg4k6FtYI",
      authDomain: "hyc-rfid-cloud-solutions-demo.firebaseapp.com",
      projectId: "hyc-rfid-cloud-solutions-demo",
      storageBucket: "hyc-rfid-cloud-solutions-demo.appspot.com",
      messagingSenderId: "363534016061",
      appId: "1:363534016061:web:57fa446e3e3d2eb5109454"
    };

    // Initialize Firebase
    app = initializeApp(firebaseConfig);

    console.log("Firebase has been initialized");

    return app;
}
}
window.lg = {
     login: function(email, password, view){
         const auth = getAuth(app);
         signInWithEmailAndPassword(auth, email, password).
               then((userCredential) => {
                 const user = userCredential.user;
                 console.log("OK");
                 view.$server.loginOk(email);
               })
               .catch((error) => {
                 view.$server.loginError();
                 console.log("ERROR");
               })
     },
 }
 window.fo = {
      forgot: function(email, view){
          const auth = getAuth(app);
          sendPasswordResetEmail(auth, email).
                then((userCredential) => {
                  view.$server.sentOk();
                })
                .catch((error) => {
                    alert("pepe");
                })
      },
  }
   window.cr = {
        create: function(email, password, view){
            const auth = getAuth(app);
            createUserWithEmailAndPassword(auth, email, password).
                  then((userCredential) => {
                    view.$server.createOk();
                  })
                  .catch((error) => {
                    view.$server.createFail();
                  })
        },
    }