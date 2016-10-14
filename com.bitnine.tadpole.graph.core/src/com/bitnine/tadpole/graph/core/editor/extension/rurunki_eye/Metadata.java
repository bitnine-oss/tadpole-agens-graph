
package com.bitnine.tadpole.graph.core.editor.extension.rurunki_eye;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Metadata {

    @SerializedName("in_edge_count")
    @Expose
    private int inEdgeCount;
    @SerializedName("out_edge_count")
    @Expose
    private int outEdgeCount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Metadata() {
    }

    /**
     * 
     * @param outEdgeCount
     * @param inEdgeCount
     */
    public Metadata(int inEdgeCount, int outEdgeCount) {
        this.inEdgeCount = inEdgeCount;
        this.outEdgeCount = outEdgeCount;
    }

    /**
     * 
     * @return
     *     The inEdgeCount
     */
    public int getInEdgeCount() {
        return inEdgeCount;
    }

    /**
     * 
     * @param inEdgeCount
     *     The in_edge_count
     */
    public void setInEdgeCount(int inEdgeCount) {
        this.inEdgeCount = inEdgeCount;
    }

    /**
     * 
     * @return
     *     The outEdgeCount
     */
    public int getOutEdgeCount() {
        return outEdgeCount;
    }

    /**
     * 
     * @param outEdgeCount
     *     The out_edge_count
     */
    public void setOutEdgeCount(int outEdgeCount) {
        this.outEdgeCount = outEdgeCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inEdgeCount).append(outEdgeCount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Metadata) == false) {
            return false;
        }
        Metadata rhs = ((Metadata) other);
        return new EqualsBuilder().append(inEdgeCount, rhs.inEdgeCount).append(outEdgeCount, rhs.outEdgeCount).isEquals();
    }

}
