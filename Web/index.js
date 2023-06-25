import express from "express";
import helmet from "helmet";
import morgan from "morgan";
import dotenv from "dotenv";
import { initializeApp } from "firebase/app";

dotenv.config();

initializeApp({
    apiKey: process.env.FIREBASE_API_KEY,
    authDomain: process.env.FIREBASE_AUTH_DOMAIN,
    projectId: process.env.FIREBASE_PROJECT_ID,
    storageBucket: process.env.FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.FIREBASE_MESSAGING_SENDER_ID,
    appId: process.env.FIREBASE_APP_ID
});

const app = express();
const port = 8800;

app.use(express.static("public"))
app.use(express.static("pages"));
app.use(helmet());
app.use(morgan("common"));

app.listen(port, () => console.log(`Server started at port ${port}`));