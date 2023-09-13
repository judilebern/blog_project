function setLanguage(lang) {
    let language = "lang="+ lang;
    let path = window.location.origin + window.location.pathname;
    let search = window.location.search;
    let newUrl;

    // atvejis, kai dar nera jokiu request atributu
    if (!search.length)
        newUrl = path + "?" + language;

    // atvejis, kai jau kazkur yra lang atributas (ir galimai yra kitu atributu)
    else if (search.includes("lang="))
        newUrl = path + search.replace(/lang=[a-zA-Z]*/, language);

    // atvejis, kai lang atributo dar nera, bet yra kitu atributu
    else
        newUrl = path + search + "&" + language;

    console.log(newUrl)
    window.open(newUrl,"_self");
}