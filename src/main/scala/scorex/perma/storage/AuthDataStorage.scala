package scorex.perma.storage

import org.h2.mvstore.{MVMap, MVStore}
import scorex.crypto.ads.merkle.AuthDataBlock
import scorex.perma.settings.PermaConstants.{DataSegment, DataSegmentIndex}
import scorex.storage.Storage

class AuthDataStorage(fileOpt: Option[String]) extends Storage[DataSegmentIndex, AuthDataBlock[DataSegment]] {

  val db = fileOpt match {
    case Some(file) => new MVStore.Builder().fileName(file).compress().open()
    case None => new MVStore.Builder().open()
  }
  val segments: MVMap[DataSegmentIndex, AuthDataBlock[DataSegment]] = db.openMap("segments")

  override def set(key: DataSegmentIndex, value: AuthDataBlock[DataSegment]): Unit = segments.put(key, value)

  override def get(key: DataSegmentIndex): Option[AuthDataBlock[DataSegment]] = Option(segments.get(key))

  override def close(): Unit = db.close()

  override def commit(): Unit = db.commit()
}
