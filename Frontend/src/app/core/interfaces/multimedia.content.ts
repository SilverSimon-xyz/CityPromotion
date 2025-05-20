import { MediaFile } from "./media.file";
import { PointOfInterest } from "./point.of.interest";

export interface MultimediaContent {
    id: number,
    title: string,
    type: FormatFileType,
    description: string,
    author: string,
    status: Status,
    mediaFileResponse: MediaFile,
    pointOfInterestResponse: PointOfInterest
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
