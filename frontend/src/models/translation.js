export default class Translation {
    constructor(id, text, transcription, translation, example, tags, type) {
        this.id = id;
        this.text = text;
        this.transcription = transcription;
        this.translation = translation;
        this.example = example;
        this.tags = tags;
        this.type = type;
    }
}