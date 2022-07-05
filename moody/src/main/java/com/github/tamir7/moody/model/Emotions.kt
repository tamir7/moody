package com.github.tamir7.moody.model

data class Emotions(val contempt: Double?,
                    val neutral: Double?,
                    val sadness: Double?,
                    val happiness: Double?,
                    val anger: Double?,
                    val disgust: Double?,
                    val fear: Double?) {
    fun getStrongestEmotion(): Emotion {
        var emotion = Emotion.Unknown
        var highestValue = 0.0

        contempt?.let {
            if (it > highestValue) {
                emotion = Emotion.Contempt
                highestValue = it
            }
        }

        neutral?.let {
            if (it > highestValue) {
                emotion = Emotion.Neutral
                highestValue = it
            }
        }

        sadness?.let {
            if (it > highestValue) {
                emotion = Emotion.Sadness
                highestValue = it
            }
        }

        happiness?.let {
            if (it > highestValue) {
                emotion = Emotion.Happiness
                highestValue = it
            }
        }

        anger?.let {
            if (it > highestValue) {
                emotion = Emotion.Anger
                highestValue = it
            }
        }

        disgust?.let {
            if (it > highestValue) {
                emotion = Emotion.Disgust
                highestValue = it
            }
        }

        fear?.let {
            if (it > highestValue) {
                emotion = Emotion.Fear
                highestValue = it
            }
        }

        return emotion
    }

}