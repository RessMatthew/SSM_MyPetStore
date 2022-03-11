package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Sequence;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper {

    Sequence getSequence(Sequence var1);
    boolean updateSequence(Sequence var1);
}
