package de.bausdorf.avm.tr064.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allowedValueRange", propOrder = {
    "minimum",
    "maximum",
    "step",
})
public class AllowedValueRangeType
{
	@XmlElement(required = true)
	private String minimum;
	@XmlElement(required = true)
	private String maximum;
	@XmlElement(required = true)
	private String step;
	/**
	 * @return the minimum
	 */
	public String getMinimum()
	{
		return minimum;
	}
	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(String minimum)
	{
		this.minimum = minimum;
	}
	/**
	 * @return the maximum
	 */
	public String getMaximum()
	{
		return maximum;
	}
	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(String maximum)
	{
		this.maximum = maximum;
	}
	/**
	 * @return the step
	 */
	public String getStep()
	{
		return step;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(String step)
	{
		this.step = step;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
				.append("min", this.minimum)
				.append("max", this.maximum)
				.append("step", this.step)
				.toString();
	}

}
