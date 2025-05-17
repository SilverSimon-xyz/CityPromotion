import { MediaFile } from "./media.file";
import { PointOfInterest } from "./point.of.interest";

export interface MultimediaContent {
    title: string,
    type: FormatFileType,
    description: string,
    author: string,
    status: Status,
    mediaFile: MediaFile,
    pointOfInterest: PointOfInterest
    createdAt: Date,
    updatedAt: Date,
}

export enum FormatFileType {
    DOCUMENT,
    IMAGE,
    AUDIO,
    VIDEO,
    OTHER
}

export enum Status {
    PENDING,
    APPROVED,
    REJECTED
}
