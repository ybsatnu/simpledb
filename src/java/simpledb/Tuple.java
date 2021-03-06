package simpledb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.*;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple implements Serializable {

    private static final long serialVersionUID = 1L;

    public TupleDesc schema; //internal Tuple Desc
    public ArrayList<Field> data; //data fields
    public RecordId id; //recordID number

    /**
     * Create a new tuple with the specified schema (type).
     *
     * @param td
     *            the schema of this tuple. It must be a valid TupleDesc
     *            instance with at least one field.
     */
    public Tuple(TupleDesc td) {
        schema = td;
        id = null;

        data = new ArrayList<Field>();
        for (int i =0; i < td.fields.size(); i++) {
            data.add(null);
        }
    }

    /**
     * @return The TupleDesc representing the schema of this tuple.
     */
    public TupleDesc getTupleDesc() {
        return schema;
    }

    /**
     * @return The RecordId representing the location of this tuple on disk. May
     *         be null.
     */
    public RecordId getRecordId() {
        return id;
    }

    /**
     * Set the RecordId information for this tuple.
     *
     * @param rid
     *            the new RecordId for this tuple.
     */
    public void setRecordId(RecordId rid) {
        id = rid;
    }


    /**
     * Change the value of the ith field of this tuple.
     *
     * @param i
     *            index of the field to change. It must be a valid index.
     * @param f
     *            new value for the field.
     */
    public void setField(int i, Field f) {
        if (i<schema.fields.size()) {
            data.set(i, f);
        }
    }

    /**
     * @return the value of the ith field, or null if it has not been set.
     *
     * @param i
     *            field index to return. Must be a valid index.
     */
    public Field getField(int i) {
        if (i<schema.fields.size()) {
            return data.get(i);
        }
        return null;
    }

    /**
     * Returns the contents of this Tuple as a string. Note that to pass the
     * system tests, the format needs to be as follows:
     *
     * column1\tcolumn2\tcolumn3\t...\tcolumnN
     *
     * where \t is any whitespace (except a newline)
     */
    public String toString() {
        String str="";
        int i;
        for(i=0; i<data.size()-1; i++){
            str+=data.get(i).toString() + "  ";
        }
        str+=data.get(i).toString();
        return str;
    }

    /**
     * @return
     *        An iterator which iterates over all the fields of this tuple
     * */
    public Iterator<Field> fields()
    {
        return this.data.iterator();
    }

    /**
     * reset the TupleDesc of this tuple (only affecting the TupleDesc)
     * */
    public void resetTupleDesc(TupleDesc td)
    {
        schema=td;
    }


    /**
     * Combines two tuples by concatenating the second to the first one
     * @param t1 first tuple to combine
     * @param t2 second tuple to combine
     * @return combined tuple
     */

    public static Tuple merge(Tuple t1, Tuple t2) {
        TupleDesc td = TupleDesc.merge(t1.getTupleDesc(), t2.getTupleDesc());

        Tuple t = new Tuple(td);

        int len1 = t1.getTupleDesc().numFields();
        int len2 = t2.getTupleDesc().numFields();

        int i = 0;
        for (int j = 0; j < len1; i++, j++)
            t.setField(i, t1.getField(j));

        for (int j = 0; j < len2; i++, j++)
            t.setField(i, t2.getField(j));

        return t;
    }


}
