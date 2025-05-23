import { MediaFile } from "./media.file";
import { PointOfInterest } from "./point.of.interest";
import { User } from "./user";

export interface Content {
    id: number,
    title: string,
    content: string,
    hashtag: string,
    author: User,
    status: Status,
    pointOfInterest: PointOfInterest,
    mediaFile?: MediaFile,
    createdAt: Date,
    updatedAt: Date,
}


export enum Status {
    PENDING,
    APPROVED,
    REJECTED
}
