import express from "express";
import { get, ref, getDatabase } from "firebase/database";

let db;
const router = express.Router();

router.get("/latest", async (req, res) => {
    const editors = (await get(ref(db, "editors"))).val() || [];
    
    let found = false;
    for (let i = 0; i < editors.length; i++) {
        if (editors[i].latest) {
            res.send(editors[i]);
            found = true;
            break;
        }
    }

    if (!found)
        res.status(404).send("No editors have been found");
});

router.get("/all", async (req, res) => {
    res.send((await get(ref(db, "editors"))).val() || []);
});

export default function() {
    db = getDatabase();
    return router;
}