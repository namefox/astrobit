import express from "express";
import { getDatabase, get, set, ref } from "firebase/database";
const router = express.Router();

router.get("/", async (req,res) => {
    const db = getDatabase();

    let success = true;
    const snapshot = await get(ref(db, "/extensions")).catch(error => {
        success = false;
        res.status(500).json(error);
    });

    if (!success) return;

    res.json(snapshot.val() || {});
});

router.get("/:extensionID", async (req,res) => {
    const db = getDatabase();

    let success = true;

    const snapshot = await get(ref(db, `/extensions/${req.params.extensionID}`)).catch(error => {
        success = false;
        res.status(500).json(error);
    });

    let val = snapshot.val();
    if (!val) {
        success = false;
        res.status(404).json("Extension does not exist.");
    }

    if (!success) return;

    res.json(val);
});

export default router;