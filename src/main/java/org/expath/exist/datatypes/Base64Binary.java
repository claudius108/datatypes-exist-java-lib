package org.expath.exist.datatypes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.exist.dom.QName;
import org.exist.xquery.BasicFunction;
import org.exist.xquery.Cardinality;
import org.exist.xquery.FunctionSignature;
import org.exist.xquery.XPathException;
import org.exist.xquery.XQueryContext;
import org.exist.xquery.value.BinaryValue;
import org.exist.xquery.value.FunctionParameterSequenceType;
import org.exist.xquery.value.FunctionReturnSequenceType;
import org.exist.xquery.value.IntegerValue;
import org.exist.xquery.value.Sequence;
import org.exist.xquery.value.SequenceType;
import org.exist.xquery.value.Type;
import org.exist.xquery.value.ValueSequence;

import ro.kuberam.libs.java.xmlDatatypes.ModuleDescription;

public class Base64Binary extends BasicFunction {

	private final static Logger logger = Logger.getLogger(Base64Binary.class);

	public final static FunctionSignature signature = new FunctionSignature(
			new QName("base64Binary-to-byte", ModuleDescription.NAMESPACE_URI, ModuleDescription.PREFIX),
			"Converts the xs:base64Binary input data to byte*.",
			new SequenceType[] { new FunctionParameterSequenceType("data", Type.ATOMIC, Cardinality.ZERO_OR_MORE,
					"The xs:base64Binary data to be converted. This parameter can be of type xs:base64Binary, or base64Binary xs:string.") },
			new FunctionReturnSequenceType(Type.BYTE, Cardinality.ZERO_OR_MORE, "converted data, as xs:byte*."));

	public Base64Binary(XQueryContext context, FunctionSignature signature) {
		super(context, signature);
	}

	@Override
	public Sequence eval(Sequence[] args, Sequence contextSequence) throws XPathException {
		Sequence result = new ValueSequence();

		Sequence data = args[0];
		byte[] resultBytes = sequence2byteArray(data);
		int resultBytesLength = resultBytes.length;
		logger.debug("resultBytesLength = " + resultBytesLength);
		logger.debug("resultBytes = " + Arrays.toString(resultBytes));

		// result = new ValueSequence();
		for (int i = 0, il = resultBytesLength; i < il; i++) {
			result.add(new IntegerValue(resultBytes[i]));
		}

		return result;
	}

	private byte[] sequence2byteArray(Sequence sequence) throws XPathException {
		byte[] result = null;

		try {
			final int itemType = sequence.itemAt(0).getType();

			switch (itemType) {
			case Type.STRING:
			case Type.ELEMENT:
			case Type.DOCUMENT:
				result = ro.kuberam.libs.java.xmlDatatypes.Base64Binary
						.ToByteArray(sequence.itemAt(0).getStringValue());
				break;
			case Type.BASE64_BINARY:
				result = binaryValueToByte((BinaryValue) sequence.itemAt(0));
				break;
			}
		} catch (Exception ex) {
			throw new XPathException(ex.getMessage());
		}

		return result;
	}

	private byte[] binaryValueToByte(BinaryValue binary) throws XPathException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			binary.streamBinaryTo(os);
			return os.toByteArray();
		} catch (final IOException ioe) {
			throw new XPathException(this, ioe);
		} finally {
			try {
				os.close();
			} catch (final IOException ex) {
			}
		}
	}
}
