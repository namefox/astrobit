const delay = ms => new Promise(res => setTimeout(res, ms));

const writers = document.querySelectorAll(".writer");
writers.forEach(writer => {
    const words = JSON.parse(writer.getAttribute("data-words"));
    const transitionDelay = parseInt(writer.getAttribute("data-transition-delay") || "0") + 300;
    const wordDelay = parseInt(writer.getAttribute("data-word-delay"));

    let word = 1;
    setInterval(async () => {
        writer.classList.add("transition");
        await delay(300);

        writer.innerText = words[word];
        writer.classList.add("second");
        
        await delay(transitionDelay);

        writer.classList.remove("second");
        writer.classList.remove("transition");

        word++;
        if (word >= words.length) word = 0;
    }, wordDelay);
});