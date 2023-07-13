import cookieParser from "cookie-parser";
import helmet from "helmet";
import morgan from "morgan";
import express from "express";
import dotenv from "dotenv";
import { initializeApp } from "firebase/app";

import editorRoutes from "./routes/editor.js";
import extensionRoutes from "./routes/extensions.js";

dotenv.config();
initializeApp({
    apiKey: process.env.FIREBASE_API_KEY,
    authDomain: process.env.FIREBASE_AUTH_DOMAIN,
    projectId: process.env.FIREBASE_PROJECT_ID,
    storageBucket: process.env.FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.FIREBASE_MESSAGING_SENDER_ID,
    appId: process.env.FIREBASE_APP_ID
}); 

const PORT = process.env.PORT || 3000;
const app = express();

app.use(express.static("views", {
    extensions: ["html", "htm"]
}));

app.use("/assets", express.static("assets"));

app.use(express.json());

app.use(cookieParser());
app.use(helmet());
app.use(morgan("common"));

app.use("/api/editor", editorRoutes());
app.use("/api/extensions", extensionRoutes());

app.listen(PORT, () => console.log(`Listening on port ${PORT}`));