const admin = require('firebase-admin');

// Initialize Firebase Admin SDK
const serviceAccount = require('D:/StudioProjects/quizapp-c985a-firebase-adminsdk-8ll26-cbefd6dab9'); // Replace with your key's path

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://quizapp-c985a-default-rtdb.firebaseio.com" // Replace with your database URL
});

const assignAdminClaim = async (uid) => {
    try {
      // Set the custom claim for the specified user
      await admin.auth().setCustomUserClaims(uid, { admin: true });
      console.log(`Admin claim assigned to user with UID: ${uid}`);
    } catch (error) {
      console.error('Error assigning custom claim:', error);
    }
  };
  
  // Call the function with the user's UID
  const userUid = 'QFc0xBzGucPoRioFw8MMjoGmgsh2'; // User ID of user to be given Admin role
  assignAdminClaim(userUid);