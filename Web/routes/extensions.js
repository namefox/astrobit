import express from "express";
import { get, ref, getDatabase } from "firebase/database";

let db;
const router = express.Router();

router.get("/:uid", async (req, res) => {
    res.json((await get(ref(db, "extensions/" + req.params.uid))).val() || []);
});

router.get("/:uid/:id", async (req, res) => {
    res.json((await get(ref(db, "extensions/" + req.params.uid + "/" + req.params.id))).val() || {});
});

router.get("/all", async (req, res) => {
    res.json((await get(ref(db, "extensions"))).val() || []);
});

export default function() {
    db = getDatabase();
    return router;
}